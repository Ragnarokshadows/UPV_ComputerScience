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

class BDITropa(BDIFieldOp):



      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)
        
        @actions.add_function(".delete", (int, tuple, ))
        def _delete(p, l):
            if p==0:
                return l[1:]
            elif p == len(l) -1:
                return l[:p]
            else:
                return l[0:p] + l[p+1:]
            

