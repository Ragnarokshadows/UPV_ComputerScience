-- *****************************
-- module SumFacts
-- *****************************

module SumFacts where
  
  fact :: Int -> Int
  fact 0 = 1
  fact n = n * fact (n - 1)
  
  sumFacts :: Int -> Int
  sumFacts 0 = 0
  sumFacts n = fact n + sumFacts (n - 1)