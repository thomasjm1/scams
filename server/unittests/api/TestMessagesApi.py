import json
import logging
import unittest

import sys

from app import AppFactory
from utilities.JsonUtility import JsonUtility
from utilities.TimestampUtility import TimestampUtility


class TestMessagesApi(unittest.TestCase):
    def setUp(self):
        console_handler = logging.StreamHandler(sys.stdout)
        root_logger = logging.getLogger()
        root_logger.setLevel(logging.DEBUG)
        root_logger.addHandler(console_handler)
        self.app = AppFactory.create_app(test_flag=True)
        self.client = self.app.test_client()
        login_response = self.client.post(
            '/api/authentication/login',
            data=json.dumps({'identifier': 'test', 'secret': 'test'}),
            content_type='application/json'
        )
        login_json = json.loads(login_response.data)
        self.access_token = login_json['access_token']

    def test_create(self):
        create_response = self.client.post(
            '/api/messages/',
            data=JsonUtility.to_json({'recipient': 'test', 'content': 'hello world', 'created': TimestampUtility.now()}),
            content_type='application/json',
            headers={'Authorization': 'Bearer {}'.format(self.access_token)}
        )
        self.assertTrue(create_response.status_code == 200)
        create_json = json.loads(create_response.data)
        self.assertTrue(create_json['operation'] == 'create')

    def test_retrieve(self):
        retrieve_response = self.client.get(
            '/api/messages/',
            content_type='application/json',
            headers={'Authorization': 'Bearer {}'.format(self.access_token)}
        )
        self.assertTrue(retrieve_response.status_code == 200)
        create_json = json.loads(retrieve_response.data)
        self.assertTrue(create_json['operation'] == 'retrieve')

    def test_update(self):
        update_response = self.client.put(
            '/api/messages/',
            content_type='application/json',
            headers={'Authorization': 'Bearer {}'.format(self.access_token)}
        )
        self.assertTrue(update_response.status_code == 200)
        create_json = json.loads(update_response.data)
        self.assertTrue(create_json['operation'] == 'update')


if __name__ == '__main__':
    unittest.main()