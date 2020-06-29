import json
import random
import math
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

# Métodos para la clase capitán
class BDICaptain(BDITroop):
      
      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)

        # Método circleFlag(int d, int p, tuple l, out tuple)
        # param:
        # -int d: radio del círculo sobre los que se generarán los puntos
        # -int p: número de puntos a generar
        # -tuple l: tupla con las coordenadas de una posición
        # return: tupla con los puntos generados
        @actions.add_function(".circleFlag", (int,int,tuple, ))
        def _circleFlag(d,p,l):
            x = l[0]
            z = l[2]

            res = []

            if p==5:
                
                punto = walk_point(d, x, z, 0)
                res.append(punto)

                punto = walk_point(d, x, z, 72*math.pi/180)
                res.append(punto)

                punto = walk_point(d, x, z, 144*math.pi/180)
                res.append(punto)

                punto = walk_point(d, x, z, 216*math.pi/180)
                res.append(punto)

                punto = walk_point(d, x, z, 288*math.pi/180)
                res.append(punto)

            elif p==4:
                
                punto = walk_point(d, x, z, 0)
                res.append(punto)

                punto = walk_point(d, x, z, 90*math.pi/180)
                res.append(punto)

                punto = walk_point(d, x, z, 180*math.pi/180)
                res.append(punto)

                punto = walk_point(d, x, z, 270*math.pi/180)
                res.append(punto)

            elif p==3:

                punto = walk_point(d, x, z, 0)
                res.append(punto)

                punto = walk_point(d, x, z, 120*math.pi/180)
                res.append(punto)

                punto = walk_point(d, x, z, 240*math.pi/180)
                res.append(punto)
            
            res = tuple(res)

            return res

        # Método walk_point(int d, double x, double z, int angle)
        # param:
        # -int d: radio del círculo sobre los que se generarán los puntos
        # -double x: coordenada en el eje x del punto de referencia
        # -double z: coordenada en el eje z del punto de referencia
        # -int angle: ángulo que deben formar los puntos sobre una línea horizontal
        # return: tupla con las coordenadas del punto generado
        def walk_point(d, x, z, angle):
            i = d

            while True:
                xx = round(x + (i * math.cos(angle)))
                zz = round(z + (i * math.sin(angle)))

                i = i - 1

                if (self.map.can_walk(xx,zz) or i == 0):
                    break

            return (xx,0,zz)

        # Método delete(int p, tuple l, out tuple)
        # param:
        # -int p: elemento que queremos eliminar
        # -tuple l: tupla a la cuál queremos eliminar un elemento
        # return: tupla con elemento p eliminado
        @actions.add_function(".delete", (int, tuple, ))
        def _delete(p, l):
            if p==0:
                return l[1:]
            elif p == len(l) -1:
                return l[:p]
            else:
                return l[0:p] + l[p+1:]
        
        # Método reverse(tuple l, out tuple)
        # param:
        # -tuple l: tupla a la cuál queremos invertir sus elementos
        # return: tupla con los elementos en orden inverso
        @actions.add_function(".reverse", (tuple, ))
        def _reverse(l):
            
            lista = list(l)
            lista.reverse()
            return tuple(lista)

        # Método flag(out boolean)
        # return: 
        # -True si la bandera sigue en el campo de visión
        # -False en el caso contrario
        @actions.add_function(".flag", ())
        def _flag():

            if not self.fov_objects:
                return False

            for tracked_object in self.fov_objects:
                
                if tracked_object.get_type() == 1003:
                    return True

            return False

        # Método friedn(tuple my_pos, tuple e_pos, out boolean)
        # param:
        # -tuple my_pos: tupla con las coordernadas de nuestro agente
        # -tuple e_pos: tupla con las coordenadas del enemigo
        # return:  
        # -True si hay un aliado en la línea de fuego entre nuestro agente 
        # -False en el caso contrario
        @actions.add_function(".friend", (tuple, tuple))
        def _friend(my_pos, e_pos):

            x1 = my_pos[0]
            z1 = my_pos[2]
            x2 = e_pos[0]
            z2 = e_pos[2]

            if not self.fov_objects or (x2 == x1 and z2 == z1):
                return False

            for tracked_object in self.fov_objects:

                if self.team == tracked_object.get_team():
                    error = False
                    x3 = tracked_object.get_position().x
                    z3 = tracked_object.get_position().z

                    if((math.isclose(x2, x1) or math.fabs(x2-x1) < 0.000001) and isBetween(z1, z2, z3)): return True
                    if((math.isclose(z2, z1) or math.fabs(z2-z1) < 0.000001) and isBetween(x1, x2, x3)): return True

                    try:
                        Ax = (x3 - x1) / (x2 - x1)
                    except:
                        error = True
                        if isBetween(z1, z2, z3):
                            return True

                    try:
                        Ay = (z3 - z1) / (z2 - z1)
                    except:
                        error = True
                        if isBetween(x1, x2, x3):
                            return True
                    if not error and math.fabs(Ax - Ay) < 0.000001 and Ax >= 0.0 and Ax <= 1.0:
                        return True

            return False

        # Método isBetween(double a, double b, double c)
        # param:
        # -double a: coordenada del primer punto
        # -double b: coordenada del segundo punto
        # -double c: coordenada del tercer punto
        # return: 
        # -True si la coordenada c está entre a y b
        # -False en el caso contrario
        def isBetween(a, b, c):
            larger = a if (a >= b) else b
            smaller = a if (a != larger) else b

            return c <= larger and c >= smaller

                               
            
            
