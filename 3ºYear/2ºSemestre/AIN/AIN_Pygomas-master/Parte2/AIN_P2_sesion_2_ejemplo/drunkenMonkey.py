import json
import random
from loguru import logger
from spade.behaviour import OneShotBehaviour
from spade.template import Template
from spade.message import Message
from pygomas.bditroop import BDITroop
from pygomas.bdifieldop import BDIFieldOp
from agentspeak import Actions
from agentspeak import grounded
from agentspeak.stdlib import actions as asp_action
from pygomas.ontology import DESTINATION

from pygomas.agent import LONG_RECEIVE_WAIT

class BDIDrunkenMonkey(BDIFieldOp):


      
      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)
        
        @actions.add(".drunkenMonkey", 0)
        def _drunkenMonkey(agent, term, intention):
            randX = random.randrange(self.map.get_size_x() - 10)
            randZ = random.randrange(self.map.get_size_z() - 10)
            while (self.map.can_walk(randX, randZ) == False):
                  randX = random.randrange(self.map.get_size_x() - 10)
                  randZ = random.randrange(self.map.get_size_z() - 10)

            self.movement.destination.x = randX
            self.movement.destination.z = randZ
            self.bdi.set_belief(DESTINATION, tuple((randX, 0, randZ),))
            yield
            
