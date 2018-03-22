import unittest

import main


class TestLoginApi(unittest.TestCase):
    def setUp(self):
        main.app.testing = True
        self.client = main.app.test_client()

    def test_index(self):
        response = self.client.get('/', environ_base={'REMOTE_ADDR': '127.0.0.1'})
        self.assertTrue(response.status_code == 200)
        self.assertTrue('127.0' in response.data.decode('utf-8'))
