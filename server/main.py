import json
import logging
import os

from flask import Flask, Response
from peewee import SqliteDatabase
from playhouse import shortcuts
from playhouse.db_url import connect

from model.messages.Message import Message
from model.messages.Message import MessageState
from model.messages.MessageBaseModel import database_proxy
from model.utilities.DateTimeEncoder import DateTimeEncoder
from repositories.MessageRepository import MessageRepository

app = Flask(__name__)

app.config['PEEWEE_DATABASE_URI'] = os.environ['PEEWEE_DATABASE_URI']

database = connect(app.config['PEEWEE_DATABASE_URI'])

database_proxy.initialize(database)
database_proxy.connect()
database_proxy.create_tables([Message], safe=True)
database_proxy.close()


@app.before_request
def before_request():
    if database_proxy.is_closed():
        database_proxy.connect()


@app.after_request
def after_request(response):
    database_proxy.close()
    return response


@app.route('/')
def index():
    message_repository = MessageRepository()
    message_repository.create_message(
        Message(
            sender="Jeremy",
            recipient="Jeremy",
            content="Hello World",
            state=MessageState.PENDING
        )
    )
    messages = message_repository.retrieve_messages()
    outputs = [shortcuts.model_to_dict(message) for message in messages]
    return Response(json.dumps(outputs, cls=DateTimeEncoder), status=200, mimetype='application/json')


@app.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500


if __name__ == '__main__':
    print("Running in debug mode with sqlite database")
    sqlite_db = SqliteDatabase('local.db')
    database_proxy.initialize(sqlite_db)
    database_proxy.connect()
    database_proxy.create_tables([Message], safe=True)
    database_proxy.close()
    app.run(host='127.0.0.1', port=8080, debug=True)
