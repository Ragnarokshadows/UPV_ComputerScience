-- *****************************
-- module Squarepow
-- *****************************

module Squarepow where

  squarepow :: Int -> Int
  squarepow x = x * x

  doubleHO :: (Int -> Int) -> Int -> Int
  doubleHO f x = f (f x)

  fourthpow :: Int -> Int
  fourthpow = doubleHO squarepow