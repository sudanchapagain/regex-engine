function matchPattern(inputLine, pattern) {
  const inputLength = inputLine.length;

  // match digits
  if (pattern === "\\d") {
    for (let i = 0; i < inputLength; i++) {
      const charCode = inputLine.charCodeAt(i);
      // ASCII codes for '0' to '9'
      if (charCode >= 48 && charCode <= 57) {
        return true;
      }
    }

    // match alphanumeric character or underscore
  } else if (pattern === "\\w") {
    for (let i = 0; i < inputLength; i++) {
      const charCode = inputLine.charCodeAt(i);
      if (
        // 0 to 9
        (charCode >= 48 && charCode <= 57) ||
        // A to Z
        (charCode >= 65 && charCode <= 90) ||
        // a to z
        (charCode >= 97 && charCode <= 122) ||
        // underscore
        charCode === 95
      ) {
        return true;
      }
    }
  } else if (pattern.length === 1) {
    return inputLine.includes(pattern);
  } else {
    throw new Error(`Unhandled pattern ${pattern}`);
  }

  return false;
}

function main() {
  const pattern = process.argv[3];
  const inputLine = require("fs").readFileSync(0, "utf-8").trim();

  if (matchPattern(inputLine, pattern)) {
    process.exit(0);
  } else {
    process.exit(1);
  }
}

main();
