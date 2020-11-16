#!/bin/bash
cd ../tools/Converter
node xlsx2lua client ../../Data out_c
node lua2json out_c out_j
node pack out_j ../../Client/UniversalIdleClient/resource/config/config.pack