for i in {1..50}; do
  curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8082/api/data
done