import json
from loguru import logger
from spade.behaviour import OneShotBehaviour
from spade.template import Template
from spade.message import Message
from pygomas.bditroop import BDITroop
from pygomas.bdifieldop import BDIFieldOp
from agentspeak import Actions
from agentspeak import grounded
from agentspeak.stdlib import actions as asp_action
from pygomas.ontology import HEALTH

from pygomas.agent import LONG_RECEIVE_WAIT

class BDIFInvencible(BDIFieldOp):



      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)
        
        @actions.add(".superhealth", 0)
        def _superhealth(agent, term, intention):
            self.health=200
            self.bdi.set_belief(HEALTH, self.health) 
            yield

      
 #       super().__init__(actions=example_agent_actions, *args, **kwargs)
