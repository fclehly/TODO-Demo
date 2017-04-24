var mongoose = require('mongoose');

var TaskSchema = new mongoose.Schema({
  title: String,
  checked: Boolean,
  create_at: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model("Task", TaskSchema);