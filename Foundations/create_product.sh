#!/bin/bash

curl -o /dev/null -s -w "%{http_code}\n" -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
	-d '{
		"productName": "'"$1"'",
		"productDesc": "'"$2"'",
		"retailPrice": "'"$3"'"
	    }'
