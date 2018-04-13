import logging
import os
from flask import Flask
from flask_jwt_extended import JWTManager
from peewee import SqliteDatabase
from playhouse.db_url import connect

from api.TelemetryApi import telemetry
from app import ConfigModule
from api.AuthenticationApi import authentication
from api.BaseApi import base
from api.MessagesApi import messages
from model.BaseModel import database_proxy
from model.identities.Identity import Identity
from model.messages.Message import Message
from model.telemetry.Telemetry import Telemetry

logger = logging.getLogger(__name__)


def create_app(test_flag):
    app = Flask(__name__)

    if test_flag is True:
        logger.debug("Setting up in testing mode")
        app.config.from_object(ConfigModule.TestingConfig)
        sqlite_file = 'local.db'
        sqlite_db = SqliteDatabase(sqlite_file)
        database_proxy.initialize(sqlite_db)
        database_proxy.create_tables([Message, Identity, Telemetry], safe=True)
        database_proxy.close()
    else:
        logger.debug("Setting up in production mode")
        app.config.from_object(ConfigModule.ProductionConfig)
        app.config['PEEWEE_DATABASE_URI'] = os.environ['PEEWEE_DATABASE_URI']
        database = connect(app.config['PEEWEE_DATABASE_URI'])
        database_proxy.initialize(database)
        database_proxy.create_tables([Message, Identity, Telemetry], safe=True)
        database_proxy.close()
    jwt = JWTManager(app)
    app.register_blueprint(authentication)
    app.register_blueprint(messages)
    app.register_blueprint(telemetry)
    app.register_blueprint(base)
    return app