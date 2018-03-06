import unittest

from model.telemetry.Telemetry import Telemetry
from repositories.TelemetryRepository import TelemetryRepository
from unittests.repositories.DbTest import DbTest


class TestTelemetryRepository(DbTest):
    def setUp(self):
        self.repository = TelemetryRepository()

    def test_create_telemetry(self):
        telemetry = self.repository.creat_telemetry(
            Telemetry(
                # test data
                t_type="TYPE1",
                content="TelemetryTest"
            )
        )
        self.assertEqual(telemetry.t_type, 'TYPE1')

    def test_retrieve_telemetry(self):
        telemetry = self.repository.creat_telemetry(
            Telemetry(
                # test data
                t_type="TYPE1",
                content="TelemetryTest"
            )
        )
        self.assertEqual(telemetry.t_type, 'TYPE1')
        telemetries = self.repository.retrieve_telemetry()
        self.assertGreater(len(telemetries), 1)


if __name__ == '__main__':
    unittest.main()
