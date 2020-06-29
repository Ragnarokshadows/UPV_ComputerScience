#!/bin/sh
#PBS -l nodes=1:ppn=8,walltime=00:10:00
#PBS -W x="NACCESSPOLICY:SINGLEJOB"
#PBS -q cpa
#PBS -d .

mpiexec ./newton2 -c 5