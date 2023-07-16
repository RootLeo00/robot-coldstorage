#!/bin/bash
if [[ $# -ne 1 ]];then exit 1; fi
git switch "$1" && mkdir "../$1" && cp -r * "../$1/" && git switch main && mv "../$1" . 
