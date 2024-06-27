const mode = import.meta.env.MODE

const noop = () => { } // No operation function

const isInDevMode = mode === 'development'

function Logger() { }

Logger.assert = isInDevMode
    ? console.assert
    : noop

Logger.count = isInDevMode
    ? console.count
    : noop

Logger.clear = isInDevMode
    ? console.clear
    : noop

Logger.error = isInDevMode
    ? console.error
    : noop

Logger.group = isInDevMode
    ? console.group
    : noop

Logger.groupCollapsed = isInDevMode
    ? console.groupCollapsed
    : noop

Logger.groupEnd = isInDevMode
    ? console.groupEnd
    : noop

Logger.info = isInDevMode
    ? console.info
    : noop

Logger.log = isInDevMode
    ? console.log
    : noop

Logger.table = isInDevMode
    ? console.table
    : noop

Logger.time = isInDevMode
    ? console.time
    : noop

Logger.timeLog = isInDevMode
    ? console.timeLog
    : noop

Logger.timeEnd = isInDevMode
    ? console.timeEnd
    : noop

Logger.warn = isInDevMode
    ? console.warn
    : noop

export default Logger