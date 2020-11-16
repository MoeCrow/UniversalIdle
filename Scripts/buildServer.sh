#!/bin/bash
cd ../tools/Converter
node xlsx2lua server ../../Data out_s
node lua2json out_s ../../Server/src/main/resources/config