package com.bemorerandom.api

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}
import com.twitter.{util => twitter}

object TwitterConverters {
  implicit def scalaToTwitterTry[T](result: Try[T]): AsTwitter[twitter.Try[T]] =
    new AsTwitter(
      result match {
        case Success(value) => twitter.Return(value)
        case Failure(exception) => twitter.Throw(exception)
      }
    )

  implicit def scalaToTwitterFuture[T](future: Future[T])(implicit ec: ExecutionContext): AsTwitter[twitter.Future[T]] =
    new AsTwitter({
      val promise = twitter.Promise[T]()
      future.onComplete(result => promise.update(result.asTwitter))
      promise
    })
}

class AsTwitter[T](op: => T) {
  def asTwitter = op
}
