-- *****************************
-- module Power version 2
-- *****************************

module Power2 where

  power2 :: Int -> Int -> Int
  power2 _ 0 = 1
  power2 n t
         | even t = power2 (n * n) (div t 2)
         | otherwise = n * power2 (n * n) (div t 2)