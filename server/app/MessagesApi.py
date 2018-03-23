import json

from flask import Blueprint, Response, logging
from playhouse import shortcuts

from model.BaseModel import database_proxy
from model.messages.Message import Message, MessageState
from repositories.MessageRepository import MessageRepository
from utilities.DateTimeEncoder import DateTimeEncoder

messages = Blueprint('messages', __name__, url_prefix='/api/messages')


@messages.before_request
def before_request():
    if database_proxy.is_closed():
        database_proxy.connect()


@messages.after_request
def after_request(response):
    database_proxy.close()
    return response


@messages.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500
