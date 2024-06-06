export const isNumber = (value: string) => !isNaN(parseInt(value, 10)) && !isNaN(parseFloat(value))