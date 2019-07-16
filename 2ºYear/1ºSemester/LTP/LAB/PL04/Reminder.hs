-- *****************************
-- module Reminder
-- *****************************

module Reminder where

  reminder :: Int -> Int -> Int
  reminder x y = if x < y then x else reminder (x - y) y