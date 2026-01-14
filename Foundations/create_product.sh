#!/bin/bash

curl -X POST http://localhost:8080/api/products \
	-H "Content-Type: application/json" \
	-d '{
		"productName": "'"$1"'",
		"productDesc": "'"$2"'",
		"retailPrice": "'"$3"'"
	    }'
