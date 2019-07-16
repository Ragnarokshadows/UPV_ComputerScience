-- *****************************
-- module Leapyear
-- *****************************

module Leapyear where

  leapyear :: Int -> Bool
  leapyear i = if mod i 4 == 0 && mod i 100 == 0 then (mod i 400 == 0) else 
               if mod i 4 == 0 && mod i 100 /= 0 then True else False