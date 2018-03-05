import logging
from model.telemetry.Telemetry import Telemetry
from repositories.PersistenceError import PersistenceError


class TelemetryRepository(object):
    logger = logging.getLogger(__name__)

    def creat_telemetry(self, telemetry):
        self.logger.debug("Inserting telemetry: {}".format(str(telemetry)))
        rows = telemetry.save()
        if rows == 1:
            self.logger.debug("Successfully inserted telemetry: {}".format(str(telemetry)))
            return telemetry
        else:
            self.logger.debug("Insert of telemetry: {} returned {}".format(str(telemetry), str(rows)))
            raise PersistenceError("Insert of telemetry: {} returned {}".format(str(telemetry), str(rows)))

    def retrieve_telemetry(self):
        self.logger.debug("Retrieving all teleteries")
        return Telemetry.select()
