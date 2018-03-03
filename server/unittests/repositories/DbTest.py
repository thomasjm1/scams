import logging
import sys
import unittest
from peewee import *

from model.messages.Message import Message
from model.messages.MessageBaseModel import database_proxy

consoleHandler = logging.StreamHandler(sys.stdout)
rootLogger = logging.getLogger()
rootLogger.setLevel(logging.DEBUG)
rootLogger.addHandler(consoleHandler)

# Configure test database
database = SqliteDatabase(':memory:')
database_proxy.initialize(database)
# Create database schema
database_proxy.create_tables([Message])


class DbTest(unittest.TestCase):
    def setUp(self):
        self.database = database
