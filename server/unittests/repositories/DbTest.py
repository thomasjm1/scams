import logging
import sys
import unittest

import os
from peewee import *

from model.messages.Message import Message
from model.BaseModel import database_proxy
from model.telemetry.Telemetry import Telemetry
from model.identities.Identity import Identity

consoleHandler = logging.StreamHandler(sys.stdout)
rootLogger = logging.getLogger()
rootLogger.setLevel(logging.DEBUG)
rootLogger.addHandler(consoleHandler)

# Configure test database
sqlite_file = 'local.db'
os.remove(sqlite_file)
database = SqliteDatabase(sqlite_file)
database.connect()
database_proxy.initialize(database)
database.create_tables([Message, Telemetry, Identity], safe=True)


class DbTest(unittest.TestCase):
    def setUp(self):
        self.database = database
