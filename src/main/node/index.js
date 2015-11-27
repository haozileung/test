var f = require("./lib/fibonacci");
function run (lang) {
  var i = 100;
  var timeTaken = 0;
  var startTime;
  var diff;

  for (; i !== 0; i--) {
    startTime = process.hrtime();
    f[lang](40);
    diff = process.hrtime(startTime);

    timeTaken = timeTaken + diff[0] * 1e9 + diff[1];
  }

  return timeTaken;
}
console.log('js  ', run('js'));
console.log('rust', run('rust'));