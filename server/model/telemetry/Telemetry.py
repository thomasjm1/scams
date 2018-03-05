import datetime
from peewee import *
from model.telemetry.TelemetryBaseModel import TelemetryBaseModel


class Telemetry(TelemetryBaseModel):
    t_type = CharField(null=False)
    content = CharField(null=False)
    created = DateTimeField(default=datetime.datetime.now)
    received = DateTimeField(default=datetime.datetime.now)
