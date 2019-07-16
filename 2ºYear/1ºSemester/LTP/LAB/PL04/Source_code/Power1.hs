-- *****************************
-- module Power version 1
-- *****************************

module Power1 where

  power1 :: Int -> Int -> Int
  power1 _ 0 = 1
  power1 n t = n * power1 n (t - 1)