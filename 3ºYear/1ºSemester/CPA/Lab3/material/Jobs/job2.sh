#!/bin/sh
#PBS -l nodes=1:ppn=1,walltime=00:10:00
#PBS -W x="NACCESSPOLICY:SINGLEJOB"
#PBS -q cpa
#PBS -d .

mpiexec ./mpi_pi2
mpiexec ./mpi_pi