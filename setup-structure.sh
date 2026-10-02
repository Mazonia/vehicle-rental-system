# Create base directory structure
mkdir -p src/main/java/com/rental
mkdir -p src/test/java/com/rental

# If files are in the wrong location, move them to correct location
# This assumes you're in the project root directory (where pom.xml is located)

# Move any files from main/java/com/rental to src/main/java/com/rental
if [ -d "main/java/com/rental" ]; then
    mv main/java/com/rental/*.java src/main/java/com/rental/ 2>/dev/null || true
    rm -rf main
fi

# Move any files from test/java/com/rental to src/test/java/com/rental
if [ -d "test/java/com/rental" ]; then
    mv test/java/com/rental/*.java src/test/java/com/rental/ 2>/dev/null || true
    rm -rf test
fi

# Move any Java files that might be in the root directory
mv *.java src/main/java/com/rental/ 2>/dev/null || true

# Clean up any empty directories
find . -type d -empty -delete

# List the current structure to verify
echo "Current directory structure:"
tree src/

# List all Java files to verify their location
echo -e "\nJava files in main:"
ls -l src/main/java/com/rental/

echo -e "\nJava files in test:"
ls -l src/test/java/com/rental/
