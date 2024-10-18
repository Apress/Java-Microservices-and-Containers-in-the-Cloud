#! /bin/bash

i=0
while true; do
  curl -s http://127.0.0.1:8081/productsweb
  echo ""
  echo "------------------------------------------"
  echo "Current date: $(date)"
  echo "=========================================="
  sleep 2
  ((i=i+1))
  echo "Firing http://127.0.0.1:8081/productsweb $i time and waiting..."
  echo ""
done

exit 0