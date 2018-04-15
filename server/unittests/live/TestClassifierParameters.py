import logging
import unittest

import sys

import requests

from app import AppFactory


class TestClassifierParameters(unittest.TestCase):

    def test_index(self):
        requests.post('http://httpbin.org/post', data={'key': 'value'})
        self.assertTrue(response.status_code == 200)


if __name__ == '__main__':
    unittest.main()
