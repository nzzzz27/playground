package controllers

import javax.inject._
import play.api.mvc._
import scala.concurrent.{ Future, ExecutionContext }
import play.api.i18n.MessagesApi

@Singleton
class AuthenticatedAction @Inject()(
  playBodyParsers: PlayBodyParsers,
  messagesApi: MessagesApi,
)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[MessagesRequest, AnyContent] with Results {

  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](
    request: Request[A],
    block:   MessagesRequest[A] => Future[Result]
  ): Future[Result] = {
    request.session.get("login_id") match {
      case Some(username) => {
        block(new MessagesRequest(request, messagesApi))
      }
      case None => {
        println("nooo");
        Future(Unauthorized)
      }
    }
  }
}
