import logging
import sys
import unittest
from peewee import *

from model.messages.Message import Message
from model.BaseModel import database_proxy
from model.telemetry.Telemetry import Telemetry
from model.clientIdentity.ClientIdentity import ClientIdentity

consoleHandler = logging.StreamHandler(sys.stdout)
rootLogger = logging.getLogger()
rootLogger.setLevel(logging.DEBUG)
rootLogger.addHandler(consoleHandler)

# Configure test database
database = SqliteDatabase(':memory:')
database_proxy.initialize(database)
database_proxy.create_tables([Message, Telemetry, ClientIdentity])


class DbTest(unittest.TestCase):
    def setUp(self):
        self.database = database
