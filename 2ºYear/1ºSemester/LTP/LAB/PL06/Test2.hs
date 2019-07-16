-- Test2.hs
import Geometry2D
main = do
   putStrLn ("The area is " ++ show (areaSquare 2))
   let other = (areaSquare 5)
   putStrLn ("Another area is " ++ show other)

