import unittest

from model.clientIdentity.ClientIdentity import ClientIdentity, ClientIdentityState
from repositories.ClientIdentityRepository import ClientIdentityRepository
from unittests.repositories.DbTest import DbTest


class TestClientIdentityRepository(DbTest):
    def setUp(self):
        self.repository = ClientIdentityRepository()

    def test_create_client_identity(self):
        client_identity = self.repository.create_identity(
            ClientIdentity(
                # test data
                identifier="Test Identifier",
                profile="Test Profile",
                recovery="Test Recovery",
                status=ClientIdentityState.INUSE
            )
        )
        self.assertEqual(client_identity.identifier, 'Test Identifier')

    def test_retrieve_client_identity(self):
        client_identity = self.repository.create_identity(
            ClientIdentity(
                # test data
                identifier="Test Identifier",
                profile="Test Profile",
                recovery="Test Recovery",
                status=ClientIdentityState.INUSE
            )
        )
        self.assertEqual(client_identity.identifier, 'Test Identifier')
        client_identities = self.repository.retrieve_identity()
        self.assertGreater(len(client_identities), 1)


if __name__ == '__main__':
    unittest.main()
