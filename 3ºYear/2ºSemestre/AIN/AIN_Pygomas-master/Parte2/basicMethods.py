import json
import random
import math
from loguru import logger
from spade.behaviour import OneShotBehaviour
from spade.template import Template
from spade.message import Message
from pygomas.bditroop import BDITroop
from pygomas.bdisoldier import BDISoldier
from pygomas.bdifieldop import BDIFieldOp
from pygomas.bdimedic import BDIMedic
from agentspeak import Actions
from agentspeak import grounded
from agentspeak.stdlib import actions as asp_action
from pygomas.ontology import DESTINATION

from pygomas.agent import LONG_RECEIVE_WAIT

# Métodos de la clase soldado
class BDISoldier_Int(BDISoldier):

      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)

        # Método faceOpposite(tuple l, tuple l2, out tuple)
        # param:
        # -tuple l: tupla con las coordenadas de una posición
        # -tuple l2: tupla con las coordenadas de otra posición
        # return: tupla con un punto a la misma distancia que los otros dos puntos pero en sentido opuesto
        @actions.add_function(".faceOpposite", (tuple, tuple, ))
        def _faceOpposite(l, l2):
            x1 = l[0]
            z1 = l[2]
            x2 = l2[0]
            z2 = l2[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            try:
                dx = (x2-x1) / lon
                dy = (z2-z1) / lon
            
            except:
                dx = 0
                dy = 0

            x3 = x1 - lon * dx
            z3 = z1 - lon * dy

            return (x3,0,z3)

        # Método distance(tuple posSol, tuple posOtro, out double)
        # param:
        # -tuple posSol: tupla con las coordenadas de la posición del agente
        # -tuple posOtro: tupla con las coordenadas de una posición
        # return: distancia entre los dos puntos
        @actions.add_function(".distance", (tuple, tuple, ))
        def _distance(posSol, posOtro):
            x1 = posSol[0]
            z1 = posSol[2]
            x2 = posOtro[0]
            z2 = posOtro[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            return lon
        
        # Método distance(tuple posSol, tuple posOtro, out double)
        # param:
        # -tuple posSol: tupla con las coordenadas de la posición del agente
        # -tuple posOtro: tupla con las coordenadas de una posición
        # return: distancia entre los dos puntos
        def distance(posSol, posOtro):
            x1 = posSol[0]
            z1 = posSol[2]
            x2 = posOtro[0]
            z2 = posOtro[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            return lon

        # Método minPos(tuple l, out int)
        # param:
        # -tuple l: tupla con números
        # return: índice del elemento mínimo
        @actions.add_function(".minPos", (tuple, ))
        def _minPos(lista):
            
            lista = list(lista)

            minpos = lista.index(min(lista))

            return minpos

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
        
        # Método optimal_point(tuple posFlag, tuple posSol, tuple posEnem, out tuple)
        # param:
        # -tuple posFlag: tupla con las coordenadas de la posición de la bandera
        # -tuple posSol: tupla con las coordenadas de la posición del soldado
        # -tuple posEnem: tupla con las coordenadas de la posición del enemigo
        # return: tupla con las coordenas del punto generado al lado de la posición del soldado
        @actions.add_function(".optimal_point", (tuple, tuple, tuple, ))
        def _optimal_point(posFlag, posSol, posEnem):
            x0 = posSol[0]
            y0 = posSol[2]
            a = posFlag[0]
            b = posFlag[2]

            if (y0 == b): y0 = y0 + 1

            ALPHA = 5

            x1 = x0+ALPHA
            x2 = x0-ALPHA
            y1 = -((x0-a)/(y0-b))*(x1-x0)+y0
            y2 = -((x0-a)/(y0-b))*(x2-x0)+y0

            p1 = (x1,0,y1)
            p2 = (x2,0,y2)
            d1 = distance(p1,posEnem)
            d2 = distance(p2,posEnem)

            if (d1<=d2): res = p1
            else: res = p2


            return res

        # Método flag(out tuple)
        # return: tupla con las coordenadas de la bandera si es avistada
        @actions.add_function(".flag", ())
        def _flag():

            if not self.fov_objects:
                return ()

            for tracked_object in self.fov_objects:
                
                if tracked_object.get_type() == 1003:
                    res = (tracked_object.get_position().x, 0, tracked_object.get_position().z)
                    return res

            return ()

        # Método friedn(tuple my_pos, tuple e_pos, out boolean)
        # param:
        # -tuple my_pos: tupla con las coordernadas de nuestro agente
        # -tuple e_pos: tupla con las coordenadas del enemigo
        # return:  
        # -True si hay un aliado en la línea de fuego entre nuestro agente 
        # -False en el caso contrario
        @actions.add_function(".friend", (tuple, tuple, ))
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

# Métodos de la clase agente de campo
class BDIFieldOp_Int(BDIFieldOp):

      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)

        # Método faceOpposite(tuple l, tuple l2, out tuple)
        # param:
        # -tuple l: tupla con las coordenadas de una posición
        # -tuple l2: tupla con las coordenadas de otra posición
        # return: tupla con un punto a la misma distancia que los otros dos puntos pero en sentido opuesto
        @actions.add_function(".faceOpposite", (tuple, tuple, ))
        def _faceOpposite(l, l2):
            x1 = l[0]
            z1 = l[2]
            x2 = l2[0]
            z2 = l2[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            try:
                dx = (x2-x1) / lon
                dy = (z2-z1) / lon
            
            except:
                dx = 0
                dy = 0

            x3 = x1 - lon * dx
            z3 = z1 - lon * dy

            return (x3,0,z3)

        # Método distance(tuple posSol, tuple posOtro, out double)
        # param:
        # -tuple posSol: tupla con las coordenadas de la posición del agente
        # -tuple posOtro: tupla con las coordenadas de una posición
        # return: distancia entre los dos puntos
        @actions.add_function(".distance", (tuple, tuple, ))
        def _distance(posSol, posOtro):
            x1 = posSol[0]
            z1 = posSol[2]
            x2 = posOtro[0]
            z2 = posOtro[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            return lon

        # Método flag(out tuple)
        # return: tupla con las coordenadas de la bandera si es avistada
        @actions.add_function(".flag", ())
        def _flag():

            if not self.fov_objects:
                return ()

            for tracked_object in self.fov_objects:
                
                if tracked_object.get_type() == 1003:
                    res = (tracked_object.get_position().x, 0, tracked_object.get_position().z)
                    return res

            return ()

        # Método friedn(tuple my_pos, tuple e_pos, out boolean)
        # param:
        # -tuple my_pos: tupla con las coordernadas de nuestro agente
        # -tuple e_pos: tupla con las coordenadas del enemigo
        # return:  
        # -True si hay un aliado en la línea de fuego entre nuestro agente 
        # -False en el caso contrario
        @actions.add_function(".friend", (tuple, tuple, ))
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

class BDIMedic_Int(BDIMedic):
      
      def add_custom_actions(self, actions):
        super().add_custom_actions(actions)

        # Método faceOpposite(tuple l, tuple l2, out tuple)
        # param:
        # -tuple l: tupla con las coordenadas de una posición
        # -tuple l2: tupla con las coordenadas de otra posición
        # return: tupla con un punto a la misma distancia que los otros dos puntos pero en sentido opuesto
        @actions.add_function(".faceOpposite", (tuple, tuple, ))
        def _faceOpposite(l, l2):
            x1 = l[0]
            z1 = l[2]
            x2 = l2[0]
            z2 = l2[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            try:
                dx = (x2-x1) / lon
                dy = (z2-z1) / lon
            
            except:
                dx = 0
                dy = 0

            x3 = x1 - lon * dx
            z3 = z1 - lon * dy

            return (x3,0,z3)
        
        # Método distance(tuple posSol, tuple posOtro, out double)
        # param:
        # -tuple posSol: tupla con las coordenadas de la posición del agente
        # -tuple posOtro: tupla con las coordenadas de una posición
        # return: distancia entre los dos puntos
        @actions.add_function(".distance", (tuple, tuple, ))
        def _distance(posSol, posOtro):
            x1 = posSol[0]
            z1 = posSol[2]
            x2 = posOtro[0]
            z2 = posOtro[2]

            lon = math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1))

            return lon
        
        # Método flag(out tuple)
        # return: tupla con las coordenadas de la bandera si es avistada
        @actions.add_function(".flag", ())
        def _flag():

            if not self.fov_objects:
                return ()

            for tracked_object in self.fov_objects:
                
                if tracked_object.get_type() == 1003:
                    res = (tracked_object.get_position().x, 0, tracked_object.get_position().z)
                    return res

            return ()

        # Método friedn(tuple my_pos, tuple e_pos, out boolean)
        # param:
        # -tuple my_pos: tupla con las coordernadas de nuestro agente
        # -tuple e_pos: tupla con las coordenadas del enemigo
        # return:  
        # -True si hay un aliado en la línea de fuego entre nuestro agente 
        # -False en el caso contrario
        @actions.add_function(".friend", (tuple, tuple, ))
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


                               
            
            
