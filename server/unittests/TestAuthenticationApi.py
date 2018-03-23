import json
import logging
import unittest

import sys

from app import AppFactory


class TestAuthenticationApi(unittest.TestCase):
    def setUp(self):
        console_handler = logging.StreamHandler(sys.stdout)
        root_logger = logging.getLogger()
        root_logger.setLevel(logging.DEBUG)
        root_logger.addHandler(console_handler)
        self.app = AppFactory.create_app(test_flag=True)
        self.client = self.app.test_client()

    def test_register(self):
        response = self.client.post(
            '/api/authentication/register',
            data=json.dumps({'identifier': 'test', 'secret': 'test'}),
            content_type='application/json'
        )
        self.assertTrue(response.status_code == 200)
        register_json = json.loads(response.data)
        register_result = register_json['register']
        self.assertTrue(register_result is not None)

    def test_login(self):
        response = self.client.post(
            '/api/authentication/login',
            data=json.dumps({'identifier': 'test', 'secret': 'test'}),
            content_type='application/json'
        )
        self.assertTrue(response.status_code == 200)
        login_json = json.loads(response.data)
        access_token = login_json['access_token']
        self.assertTrue(access_token is not None)
