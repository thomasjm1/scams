import logging
import sys
import unittest
from peewee import *

from model.messages.Message import Message
from model.messages.MessageBaseModel import database_proxy
from model.telemetry.Telemetry import Telemetry
from model.telemetry.TelemetryBaseModel import database_proxy as telemetry_dbp
from model.clientIdentity.ClientIdentity import ClientIdentity
from model.clientIdentity.ClientIdentityBaseModel import database_proxy as client_dbp

consoleHandler = logging.StreamHandler(sys.stdout)
rootLogger = logging.getLogger()
rootLogger.setLevel(logging.DEBUG)
rootLogger.addHandler(consoleHandler)

# Configure test database
database = SqliteDatabase(':memory:')

#database_proxy.initialize(database)
#telemetry_dbp.initialize(database)
client_dbp.initialize(database)

# Create database schema

#database_proxy.create_tables([Message])
#telemetry_dbp.create_tables([Telemetry])
client_dbp.create_tables([ClientIdentity])

class DbTest(unittest.TestCase):
    def setUp(self):
        self.database = database
