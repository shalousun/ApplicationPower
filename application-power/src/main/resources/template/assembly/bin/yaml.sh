#!/bin/bash

#################################################
# This function will Parse a simple YAML file
# and will output bash variables
#
# Typical Usage:
# eval $(YamlParse__parse sample.yml "PREFIX_")
#
# @param $1: The yaml file to parse
# @param $2: The prefix to append to all of the
#       variables to be created
#################################################
YamlParse__parse() {
   local prefix=$2
   local s='[[:space:]]*' w='[a-zA-Z0-9_]*' fs=$(echo @|tr @ '\034')
   sed -ne "s|^\($s\):|\1|" \
        -e "s|^\($s\)\($w\)$s:$s[\"']\(.*\)[\"']$s\$|\1$fs\2$fs\3|p" \
        -e "s|^\($s\)\($w\)$s:$s\(.*\)$s\$|\1$fs\2$fs\3|p"  $1 |
   awk -F$fs '{
      indent = length($1)/2;
      vname[indent] = $2;
      for (i in vname) {if (i > indent) {delete vname[i]}}
      if (length($3) > 0) {
         vn=""; for (i=0; i<indent; i++) {vn=(vn)(vname[i])("_")}
         printf("%s%s%s=\"%s\"\n", "'$prefix'",vn, $2, $3);
      }
   }'
}

#################################################
# @param $1: The yaml file to check if there is
#       a key
# @param $2: The key to check if it exists
# @echo: $Ash__TRUE if the key exists,
#       $Ash__FALSE otherwise
#################################################
YamlParse__has_key() {
    local line=$(grep -x "^$2:.*" "$1")
    if [[ "$line" != "" ]]; then
        echo "$Ash__TRUE"
    else
        echo "$Ash__FALSE"
    fi
}
