module.exports = {
	tabWidth: 4,
	useTabs: true,
	trailingComma: "all",
	plugins: [require.resolve("prettier-plugin-java")],
	overrides: [
		{
			files: "*.java",
			options: { parser: "java" },
		},
	],
};
