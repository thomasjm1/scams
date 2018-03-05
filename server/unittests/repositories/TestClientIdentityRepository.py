import unittest
import sys

sys.path.append("../../")
from model.clientIdentity.ClientIdentity import ClientIdentity, ClientIdentityState
from repositories.ClientIdentityRepository import ClientIdentityRepository
from unittests.repositories.DbTest import DbTest


class TestClientIdentityRepository(DbTest):
    def setUp(self):
        self.repository = ClientIdentityRepository()

    def test_create_client_identity(self):
        clientIdentity = self.repository.create_identity(
            ClientIdentity(
                #test data
                identifier = "Test Identifier",
                profile = "Test Profile",
                recovery = "Test Recovery",
                status = ClientIdentityState.INUSE
            )
        )
        self.assertEqual(clientIdentity.identifier, 'Test Identifier')


    def test_retrieve_client_identity(self):
        clientIdentity = self.repository.create_identity(
            ClientIdentity(
                #test data
                identifier = "Test Identifier",
                profile = "Test Profile",
                recovery = "Test Recovery",
                status = ClientIdentityState.INUSE
            )
        )
        self.assertEqual(clientIdentity.identifier, 'Test Identifier')
        clientIdentity = self.repository.retrieve_identity()
        self.assertGreater(len(clientIdentity), 1)


        if __name__ == '__name__':
            nuittest.main()
