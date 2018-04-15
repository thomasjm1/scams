import json
import unittest

import requests


class TestClassifierParameters(unittest.TestCase):

    def test_index(self):
        data = {
            # Any structure for content
            'content': {
                'words': ['hello', 'world'],
                'more': 'important information'
            },
            # Hardcoded key
            'key': 'p4ZfOJxhXJOU5VE9mdPX8Mo5V8dveda1bCUQaQ4QzHo06nrklJxRvdNpUZSE4WnG'
        }
        response = requests.post(
            'https://eps-scams.appspot.com/api/parameters/',
            json=data)
        self.assertTrue(response.status_code == 200)
        json_body = response.json()
        self.assertTrue(json_body['message'] == 'classifier parameters updated')


if __name__ == '__main__':
    unittest.main()
