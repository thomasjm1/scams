import logging
import os
from flask import Flask
from flask_jwt_extended import JWTManager
from peewee import SqliteDatabase
from playhouse.db_url import connect

from app import ConfigModule
from app.AuthenticationApi import authentication
from app.BaseApi import base
from app.MessagesApi import messages
from model.BaseModel import database_proxy
from model.messages.Message import Message

logger = logging.getLogger(__name__)


def create_app(test_flag):
    app = Flask(__name__)

    if test_flag is True:
        logger.debug("Setting up in testing mode")
        app.config.from_object(ConfigModule.TestingConfig)
        sqlite_db = SqliteDatabase('local.db')
        database_proxy.initialize(sqlite_db)
        database_proxy.connect()
        database_proxy.create_tables([Message], safe=True)
        database_proxy.close()
    else:
        logger.debug("Setting up in production mode")
        app.config.from_object(ConfigModule.ProductionConfig)
        app.config['PEEWEE_DATABASE_URI'] = os.environ['PEEWEE_DATABASE_URI']
        database = connect(app.config['PEEWEE_DATABASE_URI'])
        database_proxy.initialize(database)
        database_proxy.connect()
        database_proxy.create_tables([Message], safe=True)
        database_proxy.close()
    jwt = JWTManager(app)
    app.register_blueprint(authentication)
    app.register_blueprint(messages)
    app.register_blueprint(base)

    return app
