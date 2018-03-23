import logging
from model.identities.Identity import Identity
from repositories.PersistenceError import PersistenceError


class IdentityRepository(object):
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
        return Identity.select()

    #def update_identity(self, identity):
