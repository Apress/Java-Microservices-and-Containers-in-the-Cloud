#! /bin/bash

echo "Firing http://127.0.0.1:8081/productsweb and waiting..."
echo "------------------------------------------"
echo "Current date: $(date)"
echo "=========================================="
curl -s http://127.0.0.1:8081/productsweb
echo ""
echo "------------------------------------------"
echo "Current date: $(date)"
echo "=========================================="
echo "Response from http://127.0.0.1:8081/productsweb received."