import logging
from model.clientIdentity.ClientIdentity import ClientInentity
from repositories.PersistenceError import PersistenceError


class ClientIdentityRepository(object):
    logger = logging.getLogger(__name__)

    def create_identity(self, identity):
        self.logger.debug("Inserting client identity: {}".format(str(identity)))
        rows = identity.save()
        if rows == 1:
            self.logger.debug("Successfully inserted identity: {}".format(str(identity)))
            return identity
        else:
            self.logger.debug("Insert of message: {} returned {}".format(str(identity), str(rows)))
            raise PersistenceError("Insert of message: {} returned {}".format(str(identity), str(rows)))

    def retrieve_identity(self):
        self.logger.debug("Retrieving all client identities")
        return ClientInentity.select()

    #def update_identity(self, identity):
