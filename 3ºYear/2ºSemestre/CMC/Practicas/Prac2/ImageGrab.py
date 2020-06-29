import numpy as np
import time
import sys
from PIL import ImageGrab
import win32gui

toplist, winlist = [], []
def enum_cb(hwnd, results):
    winlist.append((hwnd, win32gui.GetWindowText(hwnd)))
win32gui.EnumWindows(enum_cb, toplist)

game = [(hwnd, title) for hwnd, title in winlist if 'pygame' in title.lower()]

game = game[0]
hwnd = game[0]

win32gui.SetForegroundWindow(hwnd)
bbox = win32gui.GetWindowRect(hwnd)

i = 0

#Bucle de ejecución
while i < 500:
    
    i = i + 1
    img = ImageGrab.grab(bbox)
    img.save('5.' + str(i) + '.PNG')
    time.sleep(2)