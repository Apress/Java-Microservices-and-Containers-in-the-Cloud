#! /bin/bash

i=0
while true; do
  curl -s http://192.168.64.7:32414/productsweb
  echo ""
  echo "------------------------------------------"
  echo "Current date: $(date)"
  echo "=========================================="
  sleep 2
  ((i=i+1))
  echo "Firing http://192.168.64.7:32414/productsweb $i time and waiting..."
  echo ""
done

exit 0