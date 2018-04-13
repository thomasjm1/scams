import os
import unittest

from model.identities.Identity import Identity, IdentityState
from repositories.IdentityRepository import IdentityRepository
from unittests.repositories.DbTest import DbTest

try:
    os.remove('local.db')
except:
    pass

class TestIdentityRepository(DbTest):
    def setUp(self):
        self.repository = IdentityRepository()

    def test_create_client_identity(self):
        client_identity = self.repository.create_identity(
            Identity(
                identifier="Test Identifier",
                secret="secret",
                profile="Test Profile",
                recovery="Test Recovery",
                status=IdentityState.IN_USE
            )
        )
        self.assertEqual(client_identity.identifier, 'Test Identifier')

    def test_retrieve_client_identity(self):
        client_identity = self.repository.create_identity(
            Identity(
                identifier="Test Identifier",
                secret="secret",
                profile="Test Profile",
                recovery="Test Recovery",
                status=IdentityState.IN_USE
            )
        )
        self.assertEqual(client_identity.identifier, 'Test Identifier')
        client_identities = self.repository.retrieve_identities()
        self.assertGreater(len(client_identities), 1)


if __name__ == '__main__':
    unittest.main()
